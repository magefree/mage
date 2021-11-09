package mage.cards.m;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.keyword.DisturbAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.util.functions.CopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MirrorhallMimic extends CardImpl {

    public MirrorhallMimic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);
        this.secondSideCardClazz = mage.cards.g.GhastlyMimicry.class;

        // You may have Mirrorhall Mimic enter the battlefield as a copy of any creature on the battlefield, except it's a Spirit in addition to its other types.
        this.addAbility(new EntersBattlefieldAbility(new CopyPermanentEffect(
                StaticFilters.FILTER_PERMANENT_CREATURE, new MirrorhallMimicApplier()
        ), true, null, "You may have {this} enter the battlefield as a copy of " +
                "any creature on the battlefield, except it's a Spirit in addition to its other types", null));

        // Disturb {3}{U}{U}
        this.addAbility(new DisturbAbility(this, "{3}{U}{U}"));
    }

    private MirrorhallMimic(final MirrorhallMimic card) {
        super(card);
    }

    @Override
    public MirrorhallMimic copy() {
        return new MirrorhallMimic(this);
    }
}

class MirrorhallMimicApplier extends CopyApplier {

    @Override
    public boolean apply(Game game, MageObject blueprint, Ability source, UUID targetObjectId) {
        if (!blueprint.hasSubtype(SubType.SPIRIT, game)) {
            blueprint.addSubType(game, SubType.SPIRIT);
        }
        return true;
    }
}
