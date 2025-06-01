package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.BlackWizardToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MysidianElder extends CardImpl {

    public MysidianElder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // When this creature enters, create a 0/1 black Wizard creature token with "Whenever you cast a noncreature spell, this token deals 1 damage to each opponent."
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new BlackWizardToken())));
    }

    private MysidianElder(final MysidianElder card) {
        super(card);
    }

    @Override
    public MysidianElder copy() {
        return new MysidianElder(this);
    }
}
