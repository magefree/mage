package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.abilities.Ability;
import mage.abilities.common.CardsLeaveGraveyardTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class UndeadHandNinja extends CardImpl {

    public UndeadHandNinja(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever one or more creature cards leave your graveyard, each opponent loses 1 life and you gain 1 life.
        Ability ability = new CardsLeaveGraveyardTriggeredAbility(
            new LoseLifeOpponentsEffect(1), StaticFilters.FILTER_CARD_CREATURES
        );
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private UndeadHandNinja(final UndeadHandNinja card) {
        super(card);
    }

    @Override
    public UndeadHandNinja copy() {
        return new UndeadHandNinja(this);
    }
}
