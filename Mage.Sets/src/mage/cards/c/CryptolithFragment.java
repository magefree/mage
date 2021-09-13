
package mage.cards.c;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.condition.common.XorLessLifeCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.LoseLifeAllPlayersEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

/**
 * @author fireshoes
 */
public final class CryptolithFragment extends CardImpl {

    public CryptolithFragment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.secondSideCardClazz = mage.cards.a.AuroraOfEmrakul.class;

        // Cryptolith Fragment enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add one mana of any color. Each player loses 1 life.
        Ability AnyColorManaAbility = new AnyColorManaAbility();
        AnyColorManaAbility.addEffect(new LoseLifeAllPlayersEffect(1));
        this.addAbility(AnyColorManaAbility);

        // At the beginning of your upkeep, if each player has 10 or less life, transform Cryptolith Fragment.
        this.addAbility(new TransformAbility());
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(), TargetController.YOU, false),
                new XorLessLifeCondition(XorLessLifeCondition.CheckType.EACH_PLAYER, 10),
                "At the beginning of your upkeep, if each player has 10 or less life, transform Cryptolith Fragment."));
    }

    private CryptolithFragment(final CryptolithFragment card) {
        super(card);
    }

    @Override
    public CryptolithFragment copy() {
        return new CryptolithFragment(this);
    }
}
