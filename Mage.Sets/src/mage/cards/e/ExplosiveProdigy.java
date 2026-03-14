package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.ColorsAmongControlledPermanentsCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ExplosiveProdigy extends CardImpl {

    public ExplosiveProdigy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SORCERER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Vivid -- When this creature enters, it deals X damage to target creature an opponent controls, where X is the number of colors among permanents you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new DamageTargetEffect(ColorsAmongControlledPermanentsCount.ALL_PERMANENTS)
                        .setText("it deals X damage to target creature an opponent controls, " +
                                "where X is the number of colors among permanents you control")
        );
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability.setAbilityWord(AbilityWord.VIVID).addHint(ColorsAmongControlledPermanentsCount.ALL_PERMANENTS.getHint()));
    }

    private ExplosiveProdigy(final ExplosiveProdigy card) {
        super(card);
    }

    @Override
    public ExplosiveProdigy copy() {
        return new ExplosiveProdigy(this);
    }
}
