package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterArtifactPermanent;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.DamagedPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class SchemaThief extends CardImpl {

    private static final FilterArtifactPermanent filter
            = new FilterArtifactPermanent("artifact that player controls");

    public SchemaThief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Schema Thief deals combat damage to a player, create a token that's a copy of target artifact that player controls.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new CreateTokenCopyTargetEffect(), false, true);
        ability.addTarget(new TargetPermanent(filter));
        ability.setTargetAdjuster(new DamagedPlayerControlsTargetAdjuster());
        this.addAbility(ability);
    }

    private SchemaThief(final SchemaThief card) {
        super(card);
    }

    @Override
    public SchemaThief copy() {
        return new SchemaThief(this);
    }
}

