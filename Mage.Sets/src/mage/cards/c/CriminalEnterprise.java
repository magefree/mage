package mage.cards.c;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.VillainToken;

/**
 *
 * @author muz
 */
public final class CriminalEnterprise extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.VILLAIN);

    public CriminalEnterprise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // When this enchantment enters, create a 2/1 black Villain creature token with menace.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(new VillainToken())
        ));

        // Whenever a Villain you control dies, this enchantment deals 1 damage to each opponent and you gain 1 life.
        Ability ability = new DiesCreatureTriggeredAbility(
            new DamagePlayersEffect(1, TargetController.OPPONENT), false, filter
        );
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private CriminalEnterprise(final CriminalEnterprise card) {
        super(card);
    }

    @Override
    public CriminalEnterprise copy() {
        return new CriminalEnterprise(this);
    }
}
