package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.common.AnimateDeadTriggeredAbility;
import mage.abilities.common.SacrificeIfCastAtInstantTimeTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author LevelX2, awjackson
 */
public final class Necromancy extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("creature card in a graveyard");

    public Necromancy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // You may cast Necromancy as though it had flash. If you cast it any time a sorcery couldn't have been cast, the controller of the permanent it becomes sacrifices it at the beginning of the next cleanup step.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new CastAsThoughItHadFlashSourceEffect(Duration.EndOfGame)));
        this.addAbility(new SacrificeIfCastAtInstantTimeTriggeredAbility());

        Ability ability = new AnimateDeadTriggeredAbility(true);
        ability.addTarget(new TargetCardInGraveyard(filter));
        this.addAbility(ability);
    }

    private Necromancy(final Necromancy card) {
        super(card);
    }

    @Override
    public Necromancy copy() {
        return new Necromancy(this);
    }
}
