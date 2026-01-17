package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.MayCastTargetCardEffect;
import mage.abilities.keyword.EncoreAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CastManaAdjustment;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Impulsivity extends CardImpl {

    private static final FilterCard filter = new FilterInstantOrSorceryCard("instant or sorcery card from a graveyard");

    public Impulsivity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.INCARNATION);
        this.power = new MageInt(7);
        this.toughness = new MageInt(5);

        // When this creature enters, you may cast target instant or sorcery card from a graveyard without paying its mana cost. If that spell would be put into a graveyard, exile it instead.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new MayCastTargetCardEffect(CastManaAdjustment.WITHOUT_PAYING_MANA_COST, true)
        );
        ability.addTarget(new TargetCardInGraveyard(filter));
        this.addAbility(ability);

        // Encore {7}{R}{R}
        this.addAbility(new EncoreAbility(new ManaCostsImpl<>("{7}{R}{R}")));
    }

    private Impulsivity(final Impulsivity card) {
        super(card);
    }

    @Override
    public Impulsivity copy() {
        return new Impulsivity(this);
    }
}
