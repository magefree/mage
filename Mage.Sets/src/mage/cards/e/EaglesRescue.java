package mage.cards.e;

import java.util.UUID;

import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.ComparisonType;

/**
 *
 * @author muz
 */
public final class EaglesRescue extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature you control with power 1 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.OR_LESS, 1));
    }

    public EaglesRescue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W/U}{W/U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature gets +2/+2 and has flying.
        Ability ability = new SimpleStaticAbility(new BoostEnchantedEffect(2, 2));
        ability.addEffect(new GainAbilityAttachedEffect(
                FlyingAbility.getInstance(), AttachmentType.AURA
        ).setText("and has flying"));
        this.addAbility(ability);

        // {2}{W/U}{W/U}: Return this card from your graveyard to the battlefield attached to target creature you control with power 1 or less. Activate only as a sorcery.
        ability = new ActivateAsSorceryActivatedAbility(
            Zone.GRAVEYARD,
            new EaglesRescueEffect(),
            new ManaCostsImpl<>("{2}{W/U}{W/U}")
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private EaglesRescue(final EaglesRescue card) {
        super(card);
    }

    @Override
    public EaglesRescue copy() {
        return new EaglesRescue(this);
    }
}

class EaglesRescueEffect extends OneShotEffect {

    EaglesRescueEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "Return this card from your graveyard to the battlefield attached to target creature you control with power 1 or less";
    }

    private EaglesRescueEffect(final EaglesRescueEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card aura = game.getCard(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (aura != null && controller != null
                && game.getState().getZone(aura.getId()) == Zone.GRAVEYARD) {
            Permanent targetPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (!targetPermanent.cantBeAttachedBy(aura, source, game, false)) {
                game.getState().setValue("attachTo:" + aura.getId(), targetPermanent);
                controller.moveCards(aura, Zone.BATTLEFIELD, source, game);
                return targetPermanent.addAttachment(aura.getId(), source, game);
            }
        }
        return false;
    }

    @Override
    public EaglesRescueEffect copy() {
        return new EaglesRescueEffect(this);
    }
}
