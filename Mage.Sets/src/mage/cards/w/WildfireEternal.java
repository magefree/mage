package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.AttacksAndIsNotBlockedTriggeredAbility;
import mage.abilities.effects.common.cost.CastFromHandForFreeEffect;
import mage.abilities.keyword.AfflictAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class WildfireEternal extends CardImpl {

    private static final FilterCard filter = new FilterInstantOrSorceryCard("an instant or sorcery spell");

    public WildfireEternal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.JACKAL);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Afflict 4
        this.addAbility(new AfflictAbility(4));

        // Whenever Wildfire Eternal attacks and isn't blocked, you may cast an instant or sorcery card from your hand without paying its mana cost.
        this.addAbility(new AttacksAndIsNotBlockedTriggeredAbility(
                new CastFromHandForFreeEffect(filter), false, true
        ));
    }

    private WildfireEternal(final WildfireEternal card) {
        super(card);
    }

    @Override
    public WildfireEternal copy() {
        return new WildfireEternal(this);
    }
}
