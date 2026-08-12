package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInOpponentsGraveyard;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class GollumTheAbandoned extends CardImpl {

    private static final FilterCard filterCard = new FilterCard("card from an opponent's graveyard");

    public GollumTheAbandoned(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Gollum can't block.
        this.addAbility(new CantBlockAbility());

        // When Gollum enters, exile up to one target card from an opponent's graveyard. Each opponent loses 2 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileTargetEffect());
        ability.addTarget(new TargetCardInOpponentsGraveyard(0, 1, filterCard));
        ability.addEffect(new LoseLifeOpponentsEffect(2));
        this.addAbility(ability);

        // {2}, Sacrifice an artifact or creature: Return this card from your graveyard to your hand. Activate only as a sorcery.
        Ability ability2 = new ActivateAsSorceryActivatedAbility(
            Zone.GRAVEYARD,
            new ReturnSourceFromGraveyardToHandEffect(),
            new ManaCostsImpl<>("{2}")
        );
        ability2.addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));
        this.addAbility(ability2);
    }

    private GollumTheAbandoned(final GollumTheAbandoned card) {
        super(card);
    }

    @Override
    public GollumTheAbandoned copy() {
        return new GollumTheAbandoned(this);
    }
}
