package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.MobilizeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StadiumHeadliner extends CardImpl {

    public StadiumHeadliner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Mobilize 1
        this.addAbility(new MobilizeAbility(1));

        // {1}{R}, Sacrifice this creature: It deals damage equal to the number of creatures you control to target creature.
        Effect effect = new DamageTargetEffect(CreaturesYouControlCount.PLURAL);
        effect.setText("It deals damage equal to the number of creatures you control to target creature");
        Ability ability = new SimpleActivatedAbility(effect, new ManaCostsImpl<>("{1}{R}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private StadiumHeadliner(final StadiumHeadliner card) {
        super(card);
    }

    @Override
    public StadiumHeadliner copy() {
        return new StadiumHeadliner(this);
    }
}
