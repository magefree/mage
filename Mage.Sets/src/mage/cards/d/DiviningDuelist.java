package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class DiviningDuelist extends CardImpl {

    public DiviningDuelist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When this creature enters, choose one --
        // * Tap target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect());
        ability.addTarget(new TargetCreaturePermanent());

        // * Untap target creature.
        ability.addMode(new Mode(new UntapTargetEffect()).addTarget(new TargetCreaturePermanent()));

        // * Draw a card, then discard a card.
        ability.addMode(new Mode(new DrawDiscardControllerEffect()));
        this.addAbility(ability);
    }

    private DiviningDuelist(final DiviningDuelist card) {
        super(card);
    }

    @Override
    public DiviningDuelist copy() {
        return new DiviningDuelist(this);
    }
}
