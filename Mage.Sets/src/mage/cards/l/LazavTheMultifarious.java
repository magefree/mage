package mage.cards.l;

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
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.XManaValueTargetAdjuster;
import mage.util.functions.CopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LazavTheMultifarious extends CardImpl {

    public LazavTheMultifarious(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
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
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        ability.setTargetAdjuster(new XManaValueTargetAdjuster());
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

class LazavTheMultifariousEffect extends OneShotEffect {

    LazavTheMultifariousEffect() {
        super(Outcome.Copy);
        staticText = "{this} becomes a copy of target creature card "
                + "in your graveyard with mana value X, "
                + "except its name is Lazav, the Multifarious, "
                + "it's legendary in addition to its other types, "
                + "and it has this ability";
    }

    private LazavTheMultifariousEffect(final LazavTheMultifariousEffect effect) {
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
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        ability.setTargetAdjuster(new XManaValueTargetAdjuster());
        blueprint.getAbilities().add(ability);
        blueprint.setName("Lazav, the Multifarious");
        blueprint.addSuperType(SuperType.LEGENDARY);
        return true;
    }
}
