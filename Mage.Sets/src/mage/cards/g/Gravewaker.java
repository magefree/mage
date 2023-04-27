package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author TheElk801
 */
public final class Gravewaker extends CardImpl {

    public Gravewaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {5}{B}{B}: Return target creature card from your graveyard to the battlefield tapped.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new ReturnFromGraveyardToBattlefieldTargetEffect(true)
                        .setText("return target creature card from your graveyard to the battlefield tapped"),
                new ManaCostsImpl<>("{5}{B}{B}")
        );
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);
    }

    private Gravewaker(final Gravewaker card) {
        super(card);
    }

    @Override
    public Gravewaker copy() {
        return new Gravewaker(this);
    }
}
