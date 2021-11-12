package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.permanent.token.BloodToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VoldarenEpicure extends CardImpl {

    public VoldarenEpicure(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Voldaren Epicure enters the battlefield, it deals 1 damage to each opponent. Create a Blood token.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamagePlayersEffect(
                1, TargetController.OPPONENT, "it"
        ));
        ability.addEffect(new CreateTokenEffect(new BloodToken()));
        this.addAbility(ability);
    }

    private VoldarenEpicure(final VoldarenEpicure card) {
        super(card);
    }

    @Override
    public VoldarenEpicure copy() {
        return new VoldarenEpicure(this);
    }
}
