
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInOpponentsGraveyard;

public final class CrimePunishment extends SplitCard {

    private static final FilterCard filter = new FilterCard("creature or enchantment card from an opponent's graveyard");

    static {
        filter.add(Predicates.or(CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()));
    }

    public CrimePunishment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{B}", "{X}{B}{G}", SpellAbilityType.SPLIT);

        // Crime
        // Put target creature or enchantment card from an opponent's graveyard onto the battlefield under your control.
        this.getLeftHalfCard().getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetCardInOpponentsGraveyard(filter));

        // Punishment
        // Destroy each artifact, creature, and enchantment with converted mana cost X.
        this.getRightHalfCard().getSpellAbility().addEffect(new PunishmentEffect());

    }

    private CrimePunishment(final CrimePunishment card) {
        super(card);
    }

    @Override
    public CrimePunishment copy() {
        return new CrimePunishment(this);
    }
}

class PunishmentEffect extends OneShotEffect {

    PunishmentEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy each artifact, creature, and enchantment with mana value X";
    }

    private PunishmentEffect(final PunishmentEffect effect) {
        super(effect);
    }

    @Override
    public PunishmentEffect copy() {
        return new PunishmentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(source.getControllerId(), game)) {
            if (permanent != null
                    && permanent.getManaValue() == source.getManaCostsToPay().getX()
                    && (permanent.isArtifact(game)
                    || permanent.isCreature(game)
                    || permanent.isEnchantment(game))) {
                permanent.destroy(source, game, false);
            }
        }
        return true;
    }
}
