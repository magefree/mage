package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterNonlandPermanent;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
/**
 * @author Draya
 */
public final class DihadaBinderOfWills extends CardImpl {

    private static final FilterCard legendarycreaturefilter = new FilterCreatureCard("legendary creature card");

    static {
        legendarycreaturefilter.add(SuperType.LEGENDARY.getPredicate());
    }

public DihadaBinderOfWills(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{R}{W}{B}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DIHADA);

        this.setStartingLoyalty(5);

        // +2: Up to one target legendary creature gains vigilance, lifelink, and indestructible until your next turn.

        Ability ability = new LoyaltyAbility(new GainAbilityTargetEffect(
                VigilanceAbility.getInstance(), Duration.UntilYourNextTurn
        ).setText("Up to one target legendary creature gains vigilance"), 2);
        ability.addEffect(new GainAbilityTargetEffect(
                LifelinkAbility.getInstance(), Duration.UntilYourNextTurn
        ).setText(", lifelink"));
        ability.addEffect(new GainAbilityTargetEffect(
                IndestructibleAbility.getInstance(), Duration.UntilYourNextTurn
        ).setText(", and indestructible until your next turn."));
        ability.addTarget(new TargetCreaturePermanent(0, 1, legendarycreaturefilter, false));
        this.addAbility(ability);


        // -3: Reveal the top four cards of your library. Put any number of legendary cards from among them into your hand and the rest into your graveyard. Create a Treasure token for each card put into your graveyard this way.
        effect = new DihadaFilterEffect();
        ability = new LoyaltyAbility(effect, -3);
        this.addAbility(ability);

        // -11: Gain control of all nonland permanents until end of turn. Untap them. They gain haste until end of turn.
        effect = new DihadaControlEffect();
        ability = new LoyaltyAbility(effect, -11);
        this.addAbility(ability);
        
        // Dihada, Binder of Wills can be your commander.
        this.addAbility(CanBeYourCommanderAbility.getInstance());
    }

    private DihadaBinderOfWills(final DihadaBinderOfWills card) {
        super(card);
    }

    @Override
    public DihadaBinderOfWills copy() {
        return new DihadaBinderOfWills(this);
    }
}

class DihadaFilterEffect extends OneShotEffect {

    private static final FilterCard legendaryfilter = new FilterCard("legendary card");

    static {
        legendaryfilter.add(SuperType.LEGENDARY.getPredicate());
    }

    public DihadaFilterEffect() {
        super(Outcome.PutCardInHand);
        staticText = "Reveal the top four cards of your library. Put any "
                + "number of legendary cards from among them "
                + "into your hand and the rest into your graveyard. "
                + "Create a treasure token for each card put into "
                + "your graveyard this way..";
    }

    public DihadaFilterEffect(final DihadaFilterEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller == null || sourceObject == null) {
            return false;
        }
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 4));
        if (!cards.isEmpty()) {
            controller.revealCards(sourceObject.getIdName(), cards, game);

            TargetCard target1 = new TargetCard(0, Integer.MAX_VALUE, Zone.LIBRARY, legendaryfilter);
            target1.setNotTarget(true);
            controller.choose(Outcome.PutCardInHand, cards, target1, game);
            Cards toHand = new CardsImpl(target1.getTargets());
            cards.removeAll(toHand);
            controller.moveCards(toHand.getCards(game), Zone.HAND, source, game);
            controller.moveCards(cards, Zone.GRAVEYARD, source, game);

            //Make Treasure
            new TreasureToken().putOntoBattlefield(cards.size(), game, source, source.getControllerId());
        }
        return true;
    }

    @Override
    public DihadaFilterEffect copy() {
        return new DihadaFilterEffect(this);
    }
}

class DihadaControlEffect extends OneShotEffect {

    ComparisonType type = null;
    int power = 0;

    public DihadaControlEffect() {
        super(Outcome.GainControl);
        this.staticText = "Gain control of all nonland permanents until end of turn. Untap them. They gain haste until end of turn";
    }

    public DihadaControlEffect(final DihadaControlEffect effect) {
        super(effect);
    }

    @Override
    public DihadaControlEffect copy() {
        return new DihadaControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean applied = false;

        private static final FilterPermanent controlfilter
            = new FilterNonlandPermanent("nonland permanents your opponents control");

        static {
            controlfilter.add(TargetController.OPPONENT.getControllerPredicate());
        }

        List<Permanent> dihadaperms = game.getBattlefield().getAllActivePermanents(controlfilter, game);
        for (Permanent dihadaperm : dihadaperms) {
            ContinuousEffect effect = new DihadaControlAllEffect(source.getControllerId());
            effect.setTargetPointer(new FixedTarget(dihadaperm.getId(), game));
            game.addEffect(effect, source);
            applied = true;
        }
        for (Permanent dihadaperm : dihadaperms) {
            dihadaperm.untap(game);
            applied = true;
        }
        for (Permanent dihadaperm : dihadaperms) {
            ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
            effect.setTargetPointer(new FixedTarget(dihadaperm.getId(), game));
            game.addEffect(effect, source);
            applied = true;
        }
        return applied;
    }
}

class DihadaControlAllEffect extends ContinuousEffectImpl {

    private final UUID controllerId;

    public DihadaControlAllEffect(UUID controllerId) {
        super(Duration.EndOfTurn, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        this.controllerId = controllerId;
    }

    public DihadaControlAllEffect(final DihadaControlAllEffect effect) {
        super(effect);
        this.controllerId = effect.controllerId;
    }

    @Override
    public DihadaControlAllEffect copy() {
        return new DihadaControlAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent dihadaperm = game.getPermanent(targetPointer.getFirst(game, source));
        if (dihadaperm != null && controllerId != null) {
            return dihadaperm.changeControllerId(controllerId, game, source);
        }
        return false;
    }
}
