package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.ReturnFromYourGraveyardToBattlefieldAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KnightsCharge extends CardImpl {

    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent(SubType.KNIGHT);

    private static final FilterCreatureCard filter2 = new FilterCreatureCard("Knight creature cards");
    static {
        filter2.add(SubType.KNIGHT.getPredicate());
    }

    public KnightsCharge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{B}");

        // Whenever a Knight you control attacks, each opponent loses 1 life and you gain 1 life.
        Ability ability = new AttacksCreatureYouControlTriggeredAbility(
                new LoseLifeOpponentsEffect(1), false, filter
        );
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);

        // {6}{W}{B}, Sacrifice Knights' Charge: Return all Knight creature cards from your graveyard to the battlefield.
        ability = new SimpleActivatedAbility(new ReturnFromYourGraveyardToBattlefieldAllEffect(filter2), new ManaCostsImpl<>("{6}{W}{B}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private KnightsCharge(final KnightsCharge card) {
        super(card);
    }

    @Override
    public KnightsCharge copy() {
        return new KnightsCharge(this);
    }
}
