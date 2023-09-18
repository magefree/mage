package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.NextSpellCastHasAbilityEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class FlockchaserPhantom extends CardImpl {

    public FlockchaserPhantom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{U}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Flockchaser Phantom attacks, the next spell you cast this turn has convoke.
        this.addAbility(new AttacksTriggeredAbility(new NextSpellCastHasAbilityEffect(new ConvokeAbility())));
    }

    private FlockchaserPhantom(final FlockchaserPhantom card) {
        super(card);
    }

    @Override
    public FlockchaserPhantom copy() {
        return new FlockchaserPhantom(this);
    }
}
