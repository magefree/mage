package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.ControlEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CarryAway extends CardImpl {

    public CarryAway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant Equipment
        TargetPermanent auraTarget = new TargetPermanent(StaticFilters.FILTER_PERMANENT_EQUIPMENT);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        this.addAbility(new EnchantAbility(auraTarget));

        // When Carry Away enters the battlefield, unattach enchanted Equipment.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CarryAwayEffect()));

        // You control enchanted Equipment.
        this.addAbility(new SimpleStaticAbility(new ControlEnchantedEffect("Equipment")));
    }

    private CarryAway(final CarryAway card) {
        super(card);
    }

    @Override
    public CarryAway copy() {
        return new CarryAway(this);
    }
}

class CarryAwayEffect extends OneShotEffect {

    CarryAwayEffect() {
        super(Outcome.Detriment);
        this.staticText = "unattach enchanted Equipment.";
    }

    private CarryAwayEffect(final CarryAwayEffect effect) {
        super(effect);
    }

    @Override
    public CarryAwayEffect copy() {
        return new CarryAwayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            Permanent enchantedEquipment = game.getPermanent(permanent.getAttachedTo());
            if (enchantedEquipment != null) {
                Permanent equippedCreature = game.getPermanent(enchantedEquipment.getAttachedTo());
                if (equippedCreature != null) {
                    return equippedCreature.removeAttachment(enchantedEquipment.getId(), source, game);
                }
            }
        }
        return false;
    }
}
