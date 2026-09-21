package mage.cards.i;

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
public final class InfiniteCoursework extends CardImpl {

    public InfiniteCoursework(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // When this Aura enters, tap enchanted creature. It becomes unprepared.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapEnchantedEffect());
        ability.addEffect(new InfiniteCourseworkEffect());
        this.addAbility(ability);

        // Enchanted creature loses all abilities and doesn't untap during its controller's untap step.
        Ability ability2 = new SimpleStaticAbility(new LoseAllAbilitiesAttachedEffect(AttachmentType.AURA));
        ability2.addEffect(new DontUntapInControllersUntapStepEnchantedEffect().setText("and doesn't untap during its controller's untap step"));
        this.addAbility(ability2);
    }

    private InfiniteCoursework(final InfiniteCoursework card) {
        super(card);
    }

    @Override
    public InfiniteCoursework copy() {
        return new InfiniteCoursework(this);
    }
}


class InfiniteCourseworkEffect extends OneShotEffect {

    InfiniteCourseworkEffect() {
        super(Outcome.UnboostCreature);
        staticText = "It becomes unprepared";
    }

    private InfiniteCourseworkEffect(final InfiniteCourseworkEffect effect) {
        super(effect);
    }

    @Override
    public InfiniteCourseworkEffect copy() {
        return new InfiniteCourseworkEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (permanent != null) {
            Permanent attachedTo = game.getPermanent(permanent.getAttachedTo());
            if (attachedTo != null) {
                attachedTo.setPrepared(false, game);
                return true;
            }
        }
        return false;
    }
}
