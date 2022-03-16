package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.cost.CastFromHandForFreeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OmnispellAdept extends CardImpl {

    private static final FilterCard filter = new FilterInstantOrSorceryCard("an instant or sorcery spell");

    public OmnispellAdept(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // {2}{U}, {T}: You may cast an instant or sorcery card from your hand 
        // without paying its mana cost.
        Ability ability = new SimpleActivatedAbility(
                new CastFromHandForFreeEffect(filter), new ManaCostsImpl<>("{2}{U}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private OmnispellAdept(final OmnispellAdept card) {
        super(card);
    }

    @Override
    public OmnispellAdept copy() {
        return new OmnispellAdept(this);
    }
}
