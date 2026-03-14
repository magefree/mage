package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.PutCreatureAndOrLandFromHandOntoBattlefieldEffect;
import mage.abilities.keyword.SneakAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MichelangeloImproviser extends CardImpl {

    public MichelangeloImproviser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Sneak {2}{G}{G}
        this.addAbility(new SneakAbility(this, "{2}{G}{G}"));

        // Whenever Michelangelo deals combat damage to a player, you may put a creature card and/or a land card from your hand onto the battlefield.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new PutCreatureAndOrLandFromHandOntoBattlefieldEffect()));
    }

    private MichelangeloImproviser(final MichelangeloImproviser card) {
        super(card);
    }

    @Override
    public MichelangeloImproviser copy() {
        return new MichelangeloImproviser(this);
    }
}
