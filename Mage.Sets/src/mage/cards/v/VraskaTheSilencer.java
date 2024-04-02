package mage.cards.v;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.token.TreasureAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class VraskaTheSilencer extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a nontoken creature an opponent controls");

    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public VraskaTheSilencer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GORGON);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever a nontoken creature an opponent controls dies, you may pay {1}. If you do, return that card to the battlefield tapped under your control. It's a Treasure artifact with "{T}, Sacrifice this artifact: Add one mana of any color," and it loses all other card types.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new DoIfCostPaid(new VraskaTheSilencerEffect(), new GenericManaCost(1)),
                false, filter, true
        ));
    }

    private VraskaTheSilencer(final VraskaTheSilencer card) {
        super(card);
    }

    @Override
    public VraskaTheSilencer copy() {
        return new VraskaTheSilencer(this);
    }
}

class VraskaTheSilencerEffect extends OneShotEffect {

    VraskaTheSilencerEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "return that card to the battlefield under your control. "
                + "It's a Treasure artifact with \"{T}, Sacrifice this artifact: Add one mana of any color,\" "
                + "and it loses all other card types.";
    }

    private VraskaTheSilencerEffect(final VraskaTheSilencerEffect effect) {
        super(effect);
    }

    @Override
    public VraskaTheSilencerEffect copy() {
        return new VraskaTheSilencerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card == null) {
            return false;
        }
        // Apply the continuous effect before moving the card to have the proper types on entering.
        ContinuousEffect continuousEffect = new VraskaTheSilencerContinuousEffect(new MageObjectReference(card, game, 1));
        game.addEffect(continuousEffect, source);
        controller.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null);
        return true;
    }
}

class VraskaTheSilencerContinuousEffect extends ContinuousEffectImpl {

    private final MageObjectReference mor;
    private static final Ability ability = new TreasureAbility(false);

    VraskaTheSilencerContinuousEffect(MageObjectReference mor) {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        this.staticText = "It's a Treasure artifact and loses all other card types.";
        this.mor = mor;
        dependencyTypes.add(DependencyType.ArtifactAddingRemoving);
    }

    private VraskaTheSilencerContinuousEffect(final VraskaTheSilencerContinuousEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public VraskaTheSilencerContinuousEffect copy() {
        return new VraskaTheSilencerContinuousEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        affectedObjectList.add(mor);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = affectedObjectList.get(0).getPermanent(game);
        if (permanent == null) {
            this.discard();
            return false;
        }
        switch (layer) {
            case TypeChangingEffects_4:
                permanent.setIsAllCreatureTypes(game, false);
                permanent.retainAllArtifactSubTypes(game);
                permanent.removeAllCardTypes(game);
                permanent.addCardType(game, CardType.ARTIFACT);
                permanent.addSubType(game, SubType.TREASURE);
                break;
            case AbilityAddingRemovingEffects_6:
                permanent.addAbility(ability, source.getSourceId(), game);
                break;
        }
        return true;
    }


    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6
                || layer == Layer.TypeChangingEffects_4;
    }
}
