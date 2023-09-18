package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.EternalizeAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class Dreamstealer extends CardImpl {

    public Dreamstealer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever Dreamstealer deals combat damage to a player, that player discards that many cards.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DiscardTargetEffect(SavedDamageValue.MANY),
                false, true));

        // Eternalize {4}{B}{B}
        this.addAbility(new EternalizeAbility(new ManaCostsImpl<>("{4}{B}{B}"), this));
    }

    private Dreamstealer(final Dreamstealer card) {
        super(card);
    }

    @Override
    public Dreamstealer copy() {
        return new Dreamstealer(this);
    }
}
