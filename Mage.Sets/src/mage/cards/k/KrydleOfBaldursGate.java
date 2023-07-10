package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KrydleOfBaldursGate extends CardImpl {

    public KrydleOfBaldursGate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever Krydle of Baldur's Gate deals combat damage to a player, that player loses 1 life and mills a card, then you gain 1 life and scry 1.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
                new LoseLifeTargetEffect(1), false, true
        );
        ability.addEffect(new MillCardsTargetEffect(1).setText("and mills a card"));
        ability.addEffect(new GainLifeEffect(1).concatBy(", then"));
        ability.addEffect(new ScryEffect(1, false).concatBy("and"));
        this.addAbility(ability);

        // Whenever you attack, you may pay {2}. If you do, target creature can't be blocked this turn.
        ability = new AttacksWithCreaturesTriggeredAbility(new DoIfCostPaid(
                new CantBeBlockedTargetEffect(), new GenericManaCost(2)
        ), 1);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private KrydleOfBaldursGate(final KrydleOfBaldursGate card) {
        super(card);
    }

    @Override
    public KrydleOfBaldursGate copy() {
        return new KrydleOfBaldursGate(this);
    }
}
