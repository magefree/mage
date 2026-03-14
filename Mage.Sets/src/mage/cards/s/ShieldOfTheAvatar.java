package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;


/**
 * @author emerald000
 */
public final class ShieldOfTheAvatar extends CardImpl {

    public ShieldOfTheAvatar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.subtype.add(SubType.EQUIPMENT);

        // If a source would deal damage to equipped creature, prevent X of that damage, where X is the number of creatures you control.
        this.addAbility(new SimpleStaticAbility(new ShieldOfTheAvatarPreventionEffect())
                .addHint(CreaturesYouControlHint.instance));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2)));
    }

    private ShieldOfTheAvatar(final ShieldOfTheAvatar card) {
        super(card);
    }

    @Override
    public ShieldOfTheAvatar copy() {
        return new ShieldOfTheAvatar(this);
    }
}

class ShieldOfTheAvatarPreventionEffect extends PreventionEffectImpl {

    ShieldOfTheAvatarPreventionEffect() {
        super(Duration.WhileOnBattlefield, 0, false, false);
        this.staticText = "If a source would deal damage to equipped creature, prevent X of that damage, where X is the number of creatures you control.";
    }

    private ShieldOfTheAvatarPreventionEffect(final ShieldOfTheAvatarPreventionEffect effect) {
        super(effect);
    }

    @Override
    public ShieldOfTheAvatarPreventionEffect copy() {
        return new ShieldOfTheAvatarPreventionEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        amountToPrevent = CreaturesYouControlCount.PLURAL.calculate(game, source, this);
        return super.replaceEvent(event, source, game);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            Permanent equipment = game.getPermanent(source.getSourceId());
            return equipment != null && equipment.getAttachedTo() != null
                    && event.getTargetId().equals(equipment.getAttachedTo());
        }
        return false;
    }
}
