package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author miesma
 */
public final class GoblinPlateMail extends CardImpl {

    public GoblinPlateMail(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{B/R}");

        this.subtype.add(SubType.EQUIPMENT);

        // When this Equipment enters, amass Goblins 1,
        // then attach this Equipment to the amassed Army.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GoblinPlateMailEffect()));

        // Equipped creature gets +1/+0 and has menace.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 0));
        ability.addEffect(new GainAbilityAttachedEffect(
                new MenaceAbility(), AttachmentType.EQUIPMENT
        ).setText("and has menace."));
        this.addAbility(ability);

        // Equip {4}
        this.addAbility(new EquipAbility(4));
    }

    private GoblinPlateMail(final GoblinPlateMail card) {
        super(card);
    }

    @Override
    public GoblinPlateMail copy() {
        return new GoblinPlateMail(this);
    }
}

class GoblinPlateMailEffect extends OneShotEffect {

    public GoblinPlateMailEffect() {
        super(Outcome.BoostCreature);
        staticText = "amass Goblins 1, then attach {this} to the amassed Army.";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // Amass and remember the army to attach to
        Permanent army = AmassEffect.doAmass(1, SubType.GOBLIN, game, source);
        Permanent equipment = source.getSourcePermanentIfItStillExists(game);
        if (army != null && equipment != null) {
            return army.addAttachment(source.getSourceId(), source, game);
        }
        return false;
    }

    @Override
    public GoblinPlateMailEffect copy() {
        return new GoblinPlateMailEffect();
    }
}
