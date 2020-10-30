package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.EncoreAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BriarbladeAdept extends CardImpl {

    public BriarbladeAdept(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever Briarblade Adept attacks, target creature an opponent controls get -1/-1 until end of turn.
        Ability ability = new AttacksTriggeredAbility(new BoostTargetEffect(-1, -1), false);
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);

        // Encore {3}{B}
        this.addAbility(new EncoreAbility(new ManaCostsImpl<>("{3}{B}")));
    }

    private BriarbladeAdept(final BriarbladeAdept card) {
        super(card);
    }

    @Override
    public BriarbladeAdept copy() {
        return new BriarbladeAdept(this);
    }
}
