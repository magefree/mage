package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CastSecondSpellTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801, jeffwadsworth
 */
public final class BohnBeguilingBalladeer extends CardImpl {

    public BohnBeguilingBalladeer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Each nonland card in your hand without foretell has foretell. Its foretell cost is equal to its mana cost reduced by {2}.
        this.addAbility(new SimpleStaticAbility(ForetellAbility.makeAddForetellEffect()));

        // Whenever you cast your second spell each turn, goad target creature an opponent controls.
        Ability ability = new CastSecondSpellTriggeredAbility(new GoadTargetEffect());
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private BohnBeguilingBalladeer(final BohnBeguilingBalladeer card) {
        super(card);
    }

    @Override
    public BohnBeguilingBalladeer copy() {
        return new BohnBeguilingBalladeer(this);
    }
}
