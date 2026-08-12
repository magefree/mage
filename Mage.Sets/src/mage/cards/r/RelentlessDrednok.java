package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class RelentlessDrednok extends CardImpl {

    public RelentlessDrednok(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // {2}{B}, Exile this card from your graveyard: You draw a card and lose 1 life.
        Ability ability = new SimpleActivatedAbility(
            Zone.GRAVEYARD,
            new DrawCardSourceControllerEffect(1, true),
            new ManaCostsImpl<>("{2}{B}")
        );
        ability.addCost(new ExileSourceFromGraveCost());
        ability.addEffect(new LoseLifeSourceControllerEffect(1, false).concatBy("and"));
        this.addAbility(ability);
    }

    private RelentlessDrednok(final RelentlessDrednok card) {
        super(card);
    }

    @Override
    public RelentlessDrednok copy() {
        return new RelentlessDrednok(this);
    }
}
