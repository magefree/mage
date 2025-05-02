package mage.cards.z;

import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.ReturnFromYourGraveyardToBattlefieldAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureCard;

import java.util.UUID;

/**
 *
 * @author BetaSteward
 */
public final class ZombieApocalypse extends CardImpl {

    private static final FilterCreatureCard filterZombie = new FilterCreatureCard("Zombie creature cards");
    private static final FilterPermanent filterHuman = new FilterPermanent(SubType.HUMAN, "Humans");
    static {
        filterZombie.add(SubType.ZOMBIE.getPredicate());
    }

    public ZombieApocalypse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}{B}");

        // Return all Zombie creature cards from your graveyard to the battlefield tapped, then destroy all Humans.
        this.getSpellAbility().addEffect(new ReturnFromYourGraveyardToBattlefieldAllEffect(filterZombie, true));
        this.getSpellAbility().addEffect(new DestroyAllEffect(filterHuman).concatBy(", then"));
    }

    private ZombieApocalypse(final ZombieApocalypse card) {
        super(card);
    }

    @Override
    public ZombieApocalypse copy() {
        return new ZombieApocalypse(this);
    }
}
