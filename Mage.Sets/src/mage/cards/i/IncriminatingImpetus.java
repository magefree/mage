package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.GoadAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IncriminatingImpetus extends CardImpl {

    public IncriminatingImpetus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B/R}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // When Incriminating Impetus enters the battlefield, suspect enchanted creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new IncriminatingImpetusEffect()));

        // Enchanted creature gets +2/+2 and is goaded.
        Ability ability = new SimpleStaticAbility(new BoostEnchantedEffect(2, 2));
        ability.addEffect(new GoadAttachedEffect());
        this.addAbility(ability);
    }

    private IncriminatingImpetus(final IncriminatingImpetus card) {
        super(card);
    }

    @Override
    public IncriminatingImpetus copy() {
        return new IncriminatingImpetus(this);
    }
}

class IncriminatingImpetusEffect extends OneShotEffect {

    IncriminatingImpetusEffect() {
        super(Outcome.Benefit);
        staticText = "suspect enchanted creature";
    }

    private IncriminatingImpetusEffect(final IncriminatingImpetusEffect effect) {
        super(effect);
    }

    @Override
    public IncriminatingImpetusEffect copy() {
        return new IncriminatingImpetusEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Optional.ofNullable(source.getSourcePermanentIfItStillExists(game))
                .map(Permanent::getAttachedTo)
                .map(game::getPermanent)
                .ifPresent(permanent -> permanent.setSuspected(true, game, source));
        return true;
    }
}
