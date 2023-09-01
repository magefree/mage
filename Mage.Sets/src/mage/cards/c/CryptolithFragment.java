
package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.condition.common.XorLessLifeCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.LoseLifeAllPlayersEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class CryptolithFragment extends TransformingDoubleFacedCard {

    public CryptolithFragment(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{3}",
                "Aurora of Emrakul",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELDRAZI, SubType.REFLECTION}, ""
        );
        this.getRightHalfCard().setPT(1, 4);

        // Cryptolith Fragment enters the battlefield tapped.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add one mana of any color. Each player loses 1 life.
        Ability ability = new AnyColorManaAbility();
        ability.addEffect(new LoseLifeAllPlayersEffect(1));
        this.getLeftHalfCard().addAbility(ability);

        // At the beginning of your upkeep, if each player has 10 or less life, transform Cryptolith Fragment.
        this.getLeftHalfCard().addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(), TargetController.YOU, false),
                new XorLessLifeCondition(XorLessLifeCondition.CheckType.EACH_PLAYER, 10),
                "At the beginning of your upkeep, if each player has 10 or less life, transform Cryptolith Fragment."));

        // Aurora of Emrakul
        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Deathtouch
        this.getRightHalfCard().addAbility(DeathtouchAbility.getInstance());

        // Whenever Aurora of Emrakul attacks, each opponent loses 3 life.
        this.getRightHalfCard().addAbility(new AttacksTriggeredAbility(new LoseLifeOpponentsEffect(3), false));
    }

    private CryptolithFragment(final CryptolithFragment card) {
        super(card);
    }

    @Override
    public CryptolithFragment copy() {
        return new CryptolithFragment(this);
    }
}
