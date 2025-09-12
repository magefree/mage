package mage.cards.p;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.ExileSourceAndReturnFaceUpEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PhoenixWardenOfFire extends CardImpl {

    public PhoenixWardenOfFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SAGA);
        this.subtype.add(SubType.PHOENIX);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.nightCard = true;
        this.color.setRed(true);
        this.color.setWhite(true);

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I, II -- Rising Flames -- Phoenix deals 2 damage to each opponent.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II, ability -> {
            ability.addEffect(new DamagePlayersEffect(2, TargetController.OPPONENT));
            ability.withFlavorWord("Rising Flames");
        });

        // III -- Flames of Rebirth -- Return any number of target creature cards with total mana value 6 or less from your graveyard to the battlefield. Exile Phoenix, then return it to the battlefield.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, ability -> {
            ability.addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
            ability.addEffect(new ExileSourceAndReturnFaceUpEffect());
            ability.addTarget(new PhoenixWardenOfFireTarget());
            ability.withFlavorWord("Flames of Rebirth");
        });
        this.addAbility(sagaAbility);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());
    }

    private PhoenixWardenOfFire(final PhoenixWardenOfFire card) {
        super(card);
    }

    @Override
    public PhoenixWardenOfFire copy() {
        return new PhoenixWardenOfFire(this);
    }
}

class PhoenixWardenOfFireTarget extends TargetCardInYourGraveyard {

    private static final FilterCard filterStatic = new FilterCreatureCard(
            "creature cards with total mana value 6 or less from your graveyard"
    );

    PhoenixWardenOfFireTarget() {
        super(0, Integer.MAX_VALUE, filterStatic, false);
    }

    private PhoenixWardenOfFireTarget(final PhoenixWardenOfFireTarget target) {
        super(target);
    }

    @Override
    public PhoenixWardenOfFireTarget copy() {
        return new PhoenixWardenOfFireTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        return super.canTarget(playerId, id, source, game)
                && CardUtil.checkCanTargetTotalValueLimit(
                this.getTargets(), id, MageObject::getManaValue, 6, game
        );
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        return CardUtil.checkPossibleTargetsTotalValueLimit(
                this.getTargets(),
                super.possibleTargets(sourceControllerId, source, game),
                MageObject::getManaValue, 6, game
        );
    }

    @Override
    public String getMessage(Game game) {
        // shows selected total
        int selectedValue = this
                .getTargets()
                .stream()
                .map(game::getObject)
                .filter(Objects::nonNull)
                .mapToInt(MageObject::getManaValue)
                .sum();
        return super.getMessage(game) + " (selected total mana value " + selectedValue + ")";
    }
}
