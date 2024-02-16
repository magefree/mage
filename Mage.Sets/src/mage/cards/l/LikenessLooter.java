package mage.cards.l;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetAdjuster;
import mage.util.functions.CopyApplier;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class LikenessLooter extends CardImpl {

    public LikenessLooter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}");

        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {T}: Draw a card, then discard a card.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawDiscardControllerEffect(), new TapSourceCost()));

        // {X}: Likeness Looter becomes a copy of target creature card in your graveyard with mana value X, except it has flying and this ability. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new LikenessLooterEffect(),
                new ManaCostsImpl<>("{X}")
        );
        ability.setTargetAdjuster(LikenessLooterAdjuster.instance);
        this.addAbility(ability);
    }

    private LikenessLooter(final LikenessLooter card) {
        super(card);
    }

    @Override
    public LikenessLooter copy() {
        return new LikenessLooter(this);
    }
}

enum LikenessLooterAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        int xValue = ability.getManaCostsToPay().getX();
        FilterCard filterCard = new FilterCreatureCard("creature card in your graveyard  with mana value " + xValue);
        filterCard.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, xValue));
        ability.getTargets().clear();
        ability.getTargets().add(new TargetCardInYourGraveyard(filterCard));
    }
}

class LikenessLooterEffect extends OneShotEffect {

    LikenessLooterEffect() {
        super(Outcome.Copy);
        staticText = "{this} becomes a copy of target creature card in your graveyard with mana value X, "
                + "except it has flying and this ability.";
    }

    private LikenessLooterEffect(final LikenessLooterEffect effect) {
        super(effect);
    }

    @Override
    public LikenessLooterEffect copy() {
        return new LikenessLooterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        Card copyFromCard = game.getCard(source.getFirstTarget());
        if (controller == null || permanent == null || source == null) {
            return false;
        }

        Permanent newBluePrint = new PermanentCard(copyFromCard, source.getControllerId(), game);
        newBluePrint.assignNewId();
        CopyApplier applier = new LikenessLooterCopyApplier();
        applier.apply(game, newBluePrint, source, permanent.getId());
        CopyEffect copyEffect = new CopyEffect(Duration.Custom, newBluePrint, permanent.getId());
        copyEffect.newId();
        copyEffect.setApplier(applier);
        Ability newAbility = source.copy();
        copyEffect.init(newAbility, game);
        game.addEffect(copyEffect, newAbility);
        return true;
    }
}

class LikenessLooterCopyApplier extends CopyApplier {

    @Override
    public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
        // has flying
        blueprint.getAbilities().add(FlyingAbility.getInstance());

        // has the copy ability
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new LikenessLooterEffect(),
                new ManaCostsImpl<>("{X}")
        );
        ability.setTargetAdjuster(LikenessLooterAdjuster.instance);
        blueprint.getAbilities().add(ability);
        return true;
    }
}
