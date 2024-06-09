package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.util.functions.CopyApplier;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Xanderhall
 */
public final class MalleableImpostor extends CardImpl {

    public MalleableImpostor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Flash, Flying
        this.addAbility(FlashAbility.getInstance());
        this.addAbility(FlyingAbility.getInstance());

        // You may have Malleable Impostor enter the battlefield as a copy of a creature an opponent controls, except it's a Faerie Shapeshifter in addition to its other types and it has flying.
        this.addAbility(new EntersBattlefieldAbility(
            new CopyPermanentEffect(StaticFilters.FILTER_OPPONENTS_PERMANENT_A_CREATURE, new MalleableImpostorCopyApplier()),
            true, null,
            "you may have {this} enter the battlefield as a copy of a creature an opponent controls, "+
            "except it's a Faerie Shapeshifter in addition to its other types and it has flying.", ""
        ));

    }

    private MalleableImpostor(final MalleableImpostor card) {
        super(card);
    }

    @Override
    public MalleableImpostor copy() {
        return new MalleableImpostor(this);
    }
}

class MalleableImpostorCopyApplier extends CopyApplier {

    @Override
    public boolean apply(Game game, MageObject blueprint, Ability source, UUID targetObjectId) {
        blueprint.addSubType(SubType.FAERIE, SubType.SHAPESHIFTER);
        blueprint.getAbilities().add(FlyingAbility.getInstance());
        return true;
    }

}