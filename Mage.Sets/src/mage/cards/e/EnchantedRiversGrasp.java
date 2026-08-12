package mage.cards.e;

import java.util.Optional;
import java.util.UUID;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepEnchantedEffect;
import mage.abilities.effects.common.TapEnchantedEffect;
import mage.abilities.effects.common.continuous.LoseAllAbilitiesAttachedEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class EnchantedRiversGrasp extends CardImpl {

    public EnchantedRiversGrasp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // When this Aura enters, tap enchanted creature and remove all counters from it.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapEnchantedEffect());
        ability.addEffect(new EnchantedRiversGraspEffect());
        this.addAbility(ability);

        // Enchanted creature loses all abilities and doesn't untap during its controller's untap step.
        Ability ability2 = new SimpleStaticAbility(new LoseAllAbilitiesAttachedEffect(AttachmentType.AURA));
        ability2.addEffect(new DontUntapInControllersUntapStepEnchantedEffect().setText("and doesn't untap during its controller's untap step"));
        this.addAbility(ability2);
    }

    private EnchantedRiversGrasp(final EnchantedRiversGrasp card) {
        super(card);
    }

    @Override
    public EnchantedRiversGrasp copy() {
        return new EnchantedRiversGrasp(this);
    }
}

class EnchantedRiversGraspEffect extends OneShotEffect {

    EnchantedRiversGraspEffect() {
        super(Outcome.Benefit);
        staticText = "and remove all counters from it";
    }

    private EnchantedRiversGraspEffect(final EnchantedRiversGraspEffect effect) {
        super(effect);
    }

    @Override
    public EnchantedRiversGraspEffect copy() {
        return new EnchantedRiversGraspEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable((Permanent) getValue("permanentEnteredBattlefield"))
                .map(Permanent::getAttachedTo)
                .map(game::getPermanent)
                .filter(permanent -> permanent.removeAllCounters(source, game) > 0)
                .isPresent();
    }
}
