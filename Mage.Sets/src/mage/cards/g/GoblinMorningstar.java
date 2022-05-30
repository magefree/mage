package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenAttachSourceEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.RollDieWithResultTableEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.permanent.token.GoblinToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GoblinMorningstar extends CardImpl {

    public GoblinMorningstar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{R}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+0 and has trample.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 0));
        ability.addEffect(new GainAbilityAttachedEffect(
                TrampleAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has trample"));
        this.addAbility(ability);

        // Equip {1}{R}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new ManaCostsImpl<>("{1}{R}"), false));

        // When Goblin Morningstar enters the battlefield, roll a d20.
        RollDieWithResultTableEffect effect = new RollDieWithResultTableEffect();
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect));

        // 1-9 | Create a 1/1 red Goblin creature token.
        effect.addTableEntry(1, 9, new CreateTokenEffect(new GoblinToken()));

        // 10-20 | Create a 1/1 red Goblin creature token, then attach Goblin Morningstar more it.
        effect.addTableEntry(10, 20, new CreateTokenAttachSourceEffect(new GoblinToken()));
    }

    private GoblinMorningstar(final GoblinMorningstar card) {
        super(card);
    }

    @Override
    public GoblinMorningstar copy() {
        return new GoblinMorningstar(this);
    }
}
