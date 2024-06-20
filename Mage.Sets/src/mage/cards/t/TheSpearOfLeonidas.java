package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.permanent.token.PhobosToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheSpearOfLeonidas extends CardImpl {

    public TheSpearOfLeonidas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Whenever equipped creature attacks, choose one --
        // * Bull Rush -- It gains double strike until end of turn.
        Ability ability = new AttacksAttachedTriggeredAbility(new GainAbilityAttachedEffect(
                DoubleStrikeAbility.getInstance(), AttachmentType.EQUIPMENT,
                Duration.EndOfTurn, "it gains double strike until end of turn"
        ));
        ability.withFirstModeFlavorWord("Bull Rush");

        // * Summon -- Create Phobos, a legendary 3/2 red Horse creature token.
        ability.addMode(new Mode(new CreateTokenEffect(new PhobosToken())).withFlavorWord("Summon"));

        // * Revelation -- Discard two cards, then draw two cards.
        ability.addMode(new Mode(new DiscardControllerEffect(2))
                .addEffect(new DrawCardSourceControllerEffect(2).concatBy(", then"))
                .withFlavorWord("Revelation"));
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(2, false));
    }

    private TheSpearOfLeonidas(final TheSpearOfLeonidas card) {
        super(card);
    }

    @Override
    public TheSpearOfLeonidas copy() {
        return new TheSpearOfLeonidas(this);
    }
}
