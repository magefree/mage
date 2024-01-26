package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ConvenientTarget extends CardImpl {

    public ConvenientTarget(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // When Convenient Target enters the battlefield, suspect enchanted creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ConvenientTargetEffect()));

        // Enchanted creature gets +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostEnchantedEffect(1, 1)));

        // {2}{R}: Return Convenient Target from your graveyard to your hand.
        this.addAbility(new SimpleActivatedAbility(
                Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(), new ManaCostsImpl<>("{2}{R}")
        ));
    }

    private ConvenientTarget(final ConvenientTarget card) {
        super(card);
    }

    @Override
    public ConvenientTarget copy() {
        return new ConvenientTarget(this);
    }
}

class ConvenientTargetEffect extends OneShotEffect {

    ConvenientTargetEffect() {
        super(Outcome.Benefit);
        staticText = "suspect enchanted creature";
    }

    private ConvenientTargetEffect(final ConvenientTargetEffect effect) {
        super(effect);
    }

    @Override
    public ConvenientTargetEffect copy() {
        return new ConvenientTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Optional
                .ofNullable(source.getSourcePermanentOrLKI(game))
                .map(Permanent::getAttachedTo)
                .map(game::getPermanent)
                .ifPresent(permanent -> permanent.setSuspected(true, game, source));
        return true;
    }
}
