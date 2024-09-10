package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.keyword.TheRingTemptsYouEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GollumsBite extends CardImpl {

    public GollumsBite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Target creature gets -2/-2 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-2, -2));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // {3}{B}, Exile Gollum's Bite from your graveyard: The Ring tempts you. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                Zone.GRAVEYARD, new TheRingTemptsYouEffect(), new ManaCostsImpl<>("{3}{B}")
        );
        ability.addCost(new ExileSourceFromGraveCost());
        this.addAbility(ability);
    }

    private GollumsBite(final GollumsBite card) {
        super(card);
    }

    @Override
    public GollumsBite copy() {
        return new GollumsBite(this);
    }
}
