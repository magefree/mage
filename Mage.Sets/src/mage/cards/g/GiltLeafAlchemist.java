package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ConjureCardEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;

import java.util.UUID;

public final class GiltLeafAlchemist extends CardImpl {

    private static final FilterCard filter = new FilterCard(SubType.ELF, "Elf cards");

    public GiltLeafAlchemist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(DeathtouchAbility.getInstance());

        // {T}: Conjure a card named Forest onto the battlefield. Activate only if two or more Elf cards are in your graveyard.
        Ability ability = new SimpleActivatedAbility(
                new ConjureCardEffect("Forest", Zone.BATTLEFIELD, 1), new TapSourceCost()
        ).setCondition(new CardsInControllerGraveyardCondition(2, filter));
        ability.addEffect(new InfoEffect("Activate only if two or more Elf cards are in your graveyard"));
        this.addAbility(ability);
    }

    private GiltLeafAlchemist(final GiltLeafAlchemist card) {
        super(card);
    }

    @Override
    public GiltLeafAlchemist copy() {
        return new GiltLeafAlchemist(this);
    }
}
