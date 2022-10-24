package mage.cards.b;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.Objects;
import java.util.UUID;

/**
 * @author ciaccona007
 */
public final class BrandOfIllOmen extends CardImpl {

    public BrandOfIllOmen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Cumulative upkeep {R}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{R}")));

        // Enchanted creature's controller can't cast creature spells.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BrandOfIllOmenEffect()));

    }

    private BrandOfIllOmen(final BrandOfIllOmen card) {
        super(card);
    }

    @Override
    public BrandOfIllOmen copy() {
        return new BrandOfIllOmen(this);
    }
}

class BrandOfIllOmenEffect extends ContinuousRuleModifyingEffectImpl {

    public BrandOfIllOmenEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Enchanted creature's controller can't cast creature spells";
    }

    public BrandOfIllOmenEffect(final BrandOfIllOmenEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public BrandOfIllOmenEffect copy() {
        return new BrandOfIllOmenEffect(this);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            return "You can't cast creature spells (" + mageObject.getLogName() + " on the battlefield).";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent brand = game.getPermanent(source.getSourceId());
        MageObject sourceObject = game.getObject(event.getSourceId());
        if (sourceObject != null && brand != null && brand.getAttachedTo() != null) {
            UUID enchantedController = game.getPermanent(brand.getAttachedTo()).getControllerId();
            return Objects.equals(enchantedController, event.getPlayerId()) && sourceObject.isCreature(game);
        }
        return false;
    }
}
