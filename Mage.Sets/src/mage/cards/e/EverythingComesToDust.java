package mage.cards.e;

import mage.MageObjectReference;
import mage.abilities.effects.common.ExileAllEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.UUID;

/**
 *
 * @author notgreat
 */
public final class EverythingComesToDust extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creatures except those that share a creature type with a creature that convoked this spell, all artifacts, and all enchantments");

    static {
        filter.add(EverythingComesToDustPredicate.instance);
    }
    public EverythingComesToDust(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{7}{W}{W}{W}");

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Exile all creatures except those that share a creature type with a creature that convoked this spell, all artifacts, and all enchantments.
        this.getSpellAbility().addEffect(new ExileAllEffect(filter));
    }

    private EverythingComesToDust(final EverythingComesToDust card) {
        super(card);
    }

    @Override
    public EverythingComesToDust copy() {
        return new EverythingComesToDust(this);
    }
}

enum EverythingComesToDustPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;
    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        Permanent p = input.getObject();
        if (p.isArtifact(game) || p.isEnchantment(game)){
            return true;
        }
        if (!p.isCreature(game)){
            return false;
        }
        HashSet<MageObjectReference> set = CardUtil.getSourceCostsTag(game, input.getSource(), ConvokeAbility.convokingCreaturesKey, new HashSet<>(0));
        for (MageObjectReference mor : set){
            Permanent convoked = game.getPermanentOrLKIBattlefield(mor);
            if (convoked.shareCreatureTypes(game, p)){
                return false;
            }
        }
        return true;
    }
}