
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BecomesChosenCreatureTypeSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author TheElk801
 */
public final class MistformStalker extends CardImpl {

    public MistformStalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}: Mistform Stalker becomes the creature type of your choice until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BecomesChosenCreatureTypeSourceEffect(), new GenericManaCost(1)));

        // {2}{U}{U}: Mistform Stalker gets +2/+2 and gains flying until end of turn.
        Effect effect = new BoostSourceEffect(2, 2, Duration.EndOfTurn);
        effect.setText("{this} gets +2/+2");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{2}{U}{U}"));
        effect = new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains flying until end of turn");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private MistformStalker(final MistformStalker card) {
        super(card);
    }

    @Override
    public MistformStalker copy() {
        return new MistformStalker(this);
    }
}
