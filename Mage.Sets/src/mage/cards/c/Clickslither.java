package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;

/**
 *
 * @author LevelX2
 */
public final class Clickslither extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.GOBLIN, "a Goblin");

    public Clickslither(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{R}{R}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // Sacrifice a Goblin: Clickslither gets +2/+2 and gains trample until end of turn.
        Effect effect = new BoostSourceEffect(2,2,Duration.EndOfTurn);
        effect.setText("{this} gets +2/+2");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, 
                new SacrificeTargetCost(filter));
        effect = new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains trample until end of turn");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private Clickslither(final Clickslither card) {
        super(card);
    }

    @Override
    public Clickslither copy() {
        return new Clickslither(this);
    }
}
