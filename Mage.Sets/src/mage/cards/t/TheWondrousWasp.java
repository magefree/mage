package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.LoseAllAbilitiesTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class TheWondrousWasp extends CardImpl {

    public TheWondrousWasp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Wasp's Sting -- When The Wondrous Wasp enters, tap up to one target creature. It loses all abilities for as long as The Wondrous Wasp remains on the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(
            new TapTargetEffect().setText("tap up to one target creature")
        );
        ability.addEffect(new LoseAllAbilitiesTargetEffect(Duration.WhileOnBattlefield)
            .setText("It loses all abilities for as long as {this} remains on the battlefield"));
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability.withFlavorWord("Wasp's Sting"));
    }

    private TheWondrousWasp(final TheWondrousWasp card) {
        super(card);
    }

    @Override
    public TheWondrousWasp copy() {
        return new TheWondrousWasp(this);
    }
}
