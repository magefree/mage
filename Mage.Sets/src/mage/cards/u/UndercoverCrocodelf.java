package mage.cards.u;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.DisguiseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UndercoverCrocodelf extends CardImpl {

    public UndercoverCrocodelf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{U}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.CROCODILE);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Whenever Undercover Crocodelf deals combat damage to a player, investigate.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new InvestigateEffect(), false));

        // Disguise {3}{G/U}{G/U}
        this.addAbility(new DisguiseAbility(this, new ManaCostsImpl<>("{3}{G/U}{G/U}")));
    }

    private UndercoverCrocodelf(final UndercoverCrocodelf card) {
        super(card);
    }

    @Override
    public UndercoverCrocodelf copy() {
        return new UndercoverCrocodelf(this);
    }
}
