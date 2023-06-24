
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class ArtificersHex extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("Equipment");
    static {
        filter.add(CardType.ARTIFACT.getPredicate());
        filter.add(SubType.EQUIPMENT.getPredicate());
    }

    public ArtificersHex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{B}");
        this.subtype.add(SubType.AURA);


        // Enchant Equipment
        TargetPermanent auraTarget = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        // At the beginning of your upkeep, if enchanted Equipment is attached to a creature, destroy that creature.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new ArtificersHexEffect(), TargetController.YOU, false, true));
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

    public ArtificersHexEffect() {
        super(Outcome.Benefit);
        this.staticText = "if enchanted Equipment is attached to a creature, destroy that creature";
    }

    public ArtificersHexEffect(final ArtificersHexEffect effect) {
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
