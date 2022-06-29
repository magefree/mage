package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.SaprolingToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SlimefootTheStowaway extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent(SubType.SAPROLING, "a Saproling you control");

    public SlimefootTheStowaway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.FUNGUS);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever a Saproling you control dies, Slimefoot, the Stowaway deals 1 damage to each opponent and you gain 1 life.
        Ability ability = new DiesCreatureTriggeredAbility(
                new DamagePlayersEffect(1, TargetController.OPPONENT), false, filter
        );
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);

        // {4}: Create a 1/1 green Saproling creature token.
        this.addAbility(new SimpleActivatedAbility(new CreateTokenEffect(new SaprolingToken()), new ManaCostsImpl<>("{4}")));
    }

    private SlimefootTheStowaway(final SlimefootTheStowaway card) {
        super(card);
    }

    @Override
    public SlimefootTheStowaway copy() {
        return new SlimefootTheStowaway(this);
    }
}
