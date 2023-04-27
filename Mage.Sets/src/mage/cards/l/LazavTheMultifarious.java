package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.Card;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Outcome;
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

/**
 *
 * @author TheElk801
 */
public final class LazavTheMultifarious extends CardImpl {

    public LazavTheMultifarious(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // When Lazav, the Multifarious enters the battlefield, surveil 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SurveilEffect(1), false
        ));

        // {X}: Lazav, the Multifarious becomes a copy of target creature card in your graveyard with converted mana cost X, except its name is Lazav, the Multifarious, it's legendary in addition to its other types, and it has this ability.
        Ability ability = new SimpleActivatedAbility(
                new LazavTheMultifariousEffect(),
                new ManaCostsImpl<>("{X}")
        );
        ability.setTargetAdjuster(LazavTheMultifariousAdjuster.instance);
        this.addAbility(ability);
    }

    private LazavTheMultifarious(final LazavTheMultifarious card) {
        super(card);
    }

    @Override
    public LazavTheMultifarious copy() {
        return new LazavTheMultifarious(this);
    }
}

enum LazavTheMultifariousAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        int xValue = ability.getManaCostsToPay().getX();
        FilterCard filterCard = new FilterCreatureCard("creature card with mana value " + xValue + " in your graveyard");
        filterCard.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, xValue));
        ability.getTargets().clear();
        ability.getTargets().add(new TargetCardInYourGraveyard(filterCard));
    }
}

class LazavTheMultifariousEffect extends OneShotEffect {

    LazavTheMultifariousEffect() {
        super(Outcome.Copy);
        staticText = "{this} becomes a copy of target creature card "
                + "in your graveyard with mana value X, "
                + "except its name is Lazav, the Multifarious, "
                + "it's legendary in addition to its other types, "
                + "and it has this ability";
    }

    LazavTheMultifariousEffect(final LazavTheMultifariousEffect effect) {
        super(effect);
    }

    @Override
    public LazavTheMultifariousEffect copy() {
        return new LazavTheMultifariousEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent lazavTheMultifarious = game.getPermanent(source.getSourceId());
        Permanent newBluePrint = null;
        if (controller != null
                && lazavTheMultifarious != null) {
            Card copyFromCard = game.getCard(source.getFirstTarget());
            if (copyFromCard != null) {
                newBluePrint = new PermanentCard(copyFromCard, source.getControllerId(), game);
                newBluePrint.assignNewId();
                CopyApplier applier = new LazavTheMultifariousCopyApplier();
                applier.apply(game, newBluePrint, source, lazavTheMultifarious.getId());
                CopyEffect copyEffect = new CopyEffect(Duration.Custom, newBluePrint, lazavTheMultifarious.getId());
                copyEffect.newId();
                copyEffect.setApplier(applier);
                Ability newAbility = source.copy();
                copyEffect.init(newAbility, game);
                game.addEffect(copyEffect, newAbility);
            }
            return true;
        }
        return false;
    }
}

class LazavTheMultifariousCopyApplier extends CopyApplier {

    @Override
    public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
        Ability ability = new SimpleActivatedAbility(
                new LazavTheMultifariousEffect(),
                new ManaCostsImpl<>("{X}")
        );
        ability.setTargetAdjuster(LazavTheMultifariousAdjuster.instance);
        blueprint.getAbilities().add(ability);
        blueprint.setName("Lazav, the Multifarious");
        blueprint.addSuperType(SuperType.LEGENDARY);
        return true;
    }
}
