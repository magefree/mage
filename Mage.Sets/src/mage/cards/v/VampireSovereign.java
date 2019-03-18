package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponent;

/**
 *
 * @author TheElk801
 */
public final class VampireSovereign extends CardImpl {

    public VampireSovereign(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Vampire Sovereign enters the battlefield, target opponent loses 3 life and you gain 3 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new LoseLifeTargetEffect(3));
        ability.addEffect(new GainLifeEffect(3).setText("and you gain 3 life"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public VampireSovereign(final VampireSovereign card) {
        super(card);
    }

    @Override
    public VampireSovereign copy() {
        return new VampireSovereign(this);
    }
}
