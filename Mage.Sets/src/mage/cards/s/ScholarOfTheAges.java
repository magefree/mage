package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScholarOfTheAges extends CardImpl {

    private static final FilterCard filter
            = new FilterInstantOrSorceryCard("instant and/or sorcery cards from your graveyard");

    public ScholarOfTheAges(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Scholar of the Ages enters the battlefield, return up to two target instant and/or sorcery cards from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new ReturnFromGraveyardToHandTargetEffect()
        );
        ability.addTarget(new TargetCardInYourGraveyard(0, 2, filter));
        this.addAbility(ability);
    }

    private ScholarOfTheAges(final ScholarOfTheAges card) {
        super(card);
    }

    @Override
    public ScholarOfTheAges copy() {
        return new ScholarOfTheAges(this);
    }
}
