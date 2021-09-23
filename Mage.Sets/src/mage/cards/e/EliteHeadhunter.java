package mage.cards.e;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EliteHeadhunter extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent("another creature or an artifact");

    static {
        filter.add(EliteHeadhunterPredicate.instance);
    }

    public EliteHeadhunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B/R}{B/R}{B/R}{B/R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // {B/R}{B/R}{B/R}, Sacrifice another creature or an artifact: Elite Headhunter deals 2 damage to target creature or planeswalker.
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(2), new ManaCostsImpl("{B/R}{B/R}{B/R}")
        );
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        ability.addTarget(new TargetCreatureOrPlaneswalker());
        this.addAbility(ability);
    }

    private EliteHeadhunter(final EliteHeadhunter card) {
        super(card);
    }

    @Override
    public EliteHeadhunter copy() {
        return new EliteHeadhunter(this);
    }
}

enum EliteHeadhunterPredicate implements ObjectSourcePlayerPredicate<MageObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        MageObject obj = input.getObject();
        if (obj.getId().equals(input.getSourceId())) {
            return obj.isArtifact(game);
        }
        return obj.isCreature(game) || obj.isArtifact(game);
    }
}
