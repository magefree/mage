package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
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
 * @author LevelX2
 */
public final class ArtificersHex extends CardImpl {

    public ArtificersHex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}");
        this.subtype.add(SubType.AURA);


        // Enchant Equipment
        TargetPermanent auraTarget = new TargetPermanent(StaticFilters.FILTER_PERMANENT_EQUIPMENT);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        // At the beginning of your upkeep, if enchanted Equipment is attached to a creature, destroy that creature.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new ArtificersHexEffect()));
    }

    private ArtificersHex(final ArtificersHex card) {
        super(card);
    }

    @Override
    public ArtificersHex copy() {
        return new ArtificersHex(this);
    }
}


class ArtificersHexEffect extends OneShotEffect {

    ArtificersHexEffect() {
        super(Outcome.Benefit);
        this.staticText = "if enchanted Equipment is attached to a creature, destroy that creature";
    }

    private ArtificersHexEffect(final ArtificersHexEffect effect) {
        super(effect);
    }

    @Override
    public ArtificersHexEffect copy() {
        return new ArtificersHexEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            Permanent equipment = game.getPermanent(enchantment.getAttachedTo());
            if (equipment != null && equipment.getAttachedTo() != null) {
                Permanent creature = game.getPermanent(equipment.getAttachedTo());
                if (creature != null && creature.isCreature(game)) {
                    return creature.destroy(source, game, false);
                }
            }
        }
        return false;
    }
}
